package tech.nmhillusion.slight_transportation.domains.shipper.shipper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.n2mix.exception.AppRuntimeException;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.helper.office.excel.reader.ExcelReader;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.RowData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;
import tech.nmhillusion.n2mix.util.ExceptionUtil;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.IdConstant;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.RecipientEntity;
import tech.nmhillusion.slight_transportation.entity.business.ShipperEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-01-26
 */
public interface ShipperService {

    ShipperEntity save(ShipperEntity shipperEntity);

    void deleteById(int shipperId);

    ShipperEntity findById(int shipperId);

    Page<ShipperEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    List<ShipperEntity> importExcelFile(MultipartFile excelFile);

    @TransactionalService
    class Impl implements ShipperService {

        private final ShipperRepository repository;
        private final SequenceService sequenceService;

        public Impl(ShipperRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public ShipperEntity save(ShipperEntity shipperEntity) {
            if (IdConstant.MIN_ID > shipperEntity.getShipperId()) {
                shipperEntity.setShipperId(
                        (int) sequenceService.nextValue(
                                sequenceService.generateSeqNameForClass(
                                        getClass()
                                        , ShipperEntity.ID.SHIPPER_ID.name()
                                )
                        )
                );
            }

            return repository.save(shipperEntity);
        }

        @Override
        public void deleteById(int shipperId) {
            repository.deleteById(shipperId);
        }

        @Override
        public ShipperEntity findById(int shipperId) {
            return repository.findById(shipperId).orElse(null);
        }

        @Override
        public Page<ShipperEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            final String name = StringUtil.trimWithNull(dto.get("name")).toLowerCase();
            return repository.search(
                    name,
                    PageRequest.of(pageIndex, pageSize)
            );
        }

        private List<ShipperEntity> parseShipperFromSheet(SheetData sheetData) {
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

            if (!"shipperId".equalsIgnoreCase(cells_.get(0).getStringValue())
                    || !"shipperTypeId".equalsIgnoreCase(cells_.get(1).getStringValue())
                    || !"shipperCode".equalsIgnoreCase(cells_.get(2).getStringValue())
                    || !"shipperName".equalsIgnoreCase(cells_.get(3).getStringValue())
            ) {
                LogHelper.getLogger(this).warn("Sheet does not valid to import");
                return Collections.emptyList();
            }

            final List<ShipperEntity> constructItems = rows.stream()
                    .skip(1)
                    .map(row_ -> {
                        final List<CellData> cells = row_.getCells();
                        if (2 > cells.size()) {
                            throw new AppRuntimeException("Does not enough cell to construct a recipient: " + cells);
                        }

                        final String shipperId = cells.get(0).getStringValue();
                        final String shipperTypeId = cells.get(1).getStringValue();
                        final String shipperCode = cells.get(2).getStringValue();
                        final String shipperName = cells.get(3).getStringValue();

                        return new ShipperEntity()
                                .setShipperId(
                                        (int) Double.parseDouble(shipperId)
                                )
                                .setShipperTypeId(
                                        (int) Double.parseDouble(shipperTypeId)
                                )
                                .setShipperCode(shipperCode)
                                .setShipperName(shipperName)
                                ;
                    })
                    .collect(Collectors.toList());

            return constructItems;
        }

        private void moveToMaxRecipientIdForSequence() {
            final long maxId = repository.getMaxId();
            sequenceService.modifyCurrentValue(
                    sequenceService.generateSeqNameForClass(getClass(), RecipientEntity.ID.RECIPIENT_ID.name())
                    , maxId
            );
        }

        @Override
        public List<ShipperEntity> importExcelFile(MultipartFile excelFile) {
            try {
                final List<SheetData> sheets = ExcelReader.read(excelFile.getInputStream());

                final List<ShipperEntity> totalRecipientList = new ArrayList<>();

                for (SheetData sheetData_ : sheets) {
                    final List<ShipperEntity> itemList = parseShipperFromSheet(sheetData_);
                    totalRecipientList.addAll(itemList);
                }

                repository.saveAllAndFlush(totalRecipientList);
                moveToMaxRecipientIdForSequence();

                return totalRecipientList;
            } catch (Throwable ex) {
                throw ExceptionUtil.throwException(ex);
            }
        }
    }
}
