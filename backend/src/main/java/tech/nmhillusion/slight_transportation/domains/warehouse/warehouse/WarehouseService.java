package tech.nmhillusion.slight_transportation.domains.warehouse.warehouse;

import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.n2mix.helper.office.excel.reader.ExcelReader;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.RowData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;
import tech.nmhillusion.n2mix.util.ExceptionUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.WarehouseEntity;
import tech.nmhillusion.slight_transportation.util.NumberUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-08
 */
public interface WarehouseService {
    List<WarehouseEntity> findAll();

    WarehouseEntity sync(WarehouseEntity warehouseEntity);

    WarehouseEntity findById(int warehouseId);

    List<WarehouseEntity> importExcelFile(MultipartFile excelFile);

    double remainingQuantityOfCommodityOfWarehouse(int warehouseId, long commodityId);

    @TransactionalService
    class Impl implements WarehouseService {
        private final WarehouseRepository repository;
        private final SequenceService sequenceService;

        public Impl(WarehouseRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public List<WarehouseEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public WarehouseEntity sync(WarehouseEntity warehouseEntity) {
            final WarehouseEntity savedEntity = repository.save(warehouseEntity);
            getLogger(this).info("savedEntity: {}", savedEntity);
            return savedEntity;
        }

        @Override
        public WarehouseEntity findById(int warehouseId) {
            return repository.findById(warehouseId).orElse(null);
        }

        @Override
        public List<WarehouseEntity> importExcelFile(MultipartFile excelFile) {
            try {
                final List<SheetData> sheets = ExcelReader.read(excelFile.getInputStream());

                final List<WarehouseEntity> totalWarehouseList = new ArrayList<>();

                for (SheetData sheetData_ : sheets) {
                    totalWarehouseList.addAll(
                            parseWarehouseFromSheet(sheetData_)
                    );
                }

                repository.saveAllAndFlush(totalWarehouseList);
                sequenceService.modifyCurrentValue(
                        sequenceService.generateSeqNameForClass(getClass(), WarehouseEntity.ID.WAREHOUSE_ID.name())
                        , repository.getMaxId()
                );

                return totalWarehouseList;
            } catch (Throwable ex) {
                throw ExceptionUtil.throwException(ex);
            }
        }

        private Collection<? extends WarehouseEntity> parseWarehouseFromSheet(SheetData sheetData) {
            final List<RowData> rows = sheetData.getRows();
            if (null == rows) {
                throw new IllegalArgumentException("Rows is null for this sheet: " + sheetData.getSheetName());
            }

            final RowData firstRow = rows.getFirst();
            if (null == firstRow) {
                throw new IllegalArgumentException("First row is null for this sheet: " + sheetData.getSheetName());
            }

            final List<CellData> cells_ = firstRow.getCells();
            if (null == cells_) {
                throw new IllegalArgumentException("Cells is null for this sheet: " + sheetData.getSheetName());
            }

            if (!"warehouseId".equalsIgnoreCase(cells_.get(0).getStringValue())
                    || !"warehouseName".equalsIgnoreCase(cells_.get(1).getStringValue())
                    || !"warehouseAddress".equalsIgnoreCase(cells_.get(2).getStringValue())
            ) {
                throw new IllegalArgumentException("Invalid first row for this sheet: " + sheetData.getSheetName());
            }

            final List<WarehouseEntity> constructItems = rows.stream()
                    .skip(1)
                    .map(row_ -> {
                        final List<CellData> cells = row_.getCells();
                        if (3 > cells.size()) {
                            throw new IllegalArgumentException("Invalid row for this sheet: " + sheetData.getSheetName());
                        }

                        final WarehouseEntity warehouseEntity = new WarehouseEntity();
                        warehouseEntity.setWarehouseId(
                                NumberUtil.parseStringFromDoubleToLong(cells.get(0).getStringValue())
                        );
                        warehouseEntity.setWarehouseName(cells.get(1).getStringValue());
                        warehouseEntity.setWarehouseAddress(cells.get(2).getStringValue());

                        return warehouseEntity;
                    })
                    .toList();

            getLogger(this).info("constructItems: {}", constructItems);
            return constructItems;
        }

        @Override
        public double remainingQuantityOfCommodityOfWarehouse(int warehouseId, long commodityId) {
            return repository.sumQuantityOfCommodityOfWarehouse(warehouseId, commodityId)
                    - repository.sumUsedQuantityOfCommodityOfWarehouse(warehouseId, commodityId)
                    ;
        }
    }
}
