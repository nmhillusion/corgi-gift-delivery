package tech.nmhillusion.slight_transportation.domains.commodity.commodity;

import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.n2mix.exception.AppRuntimeException;
import tech.nmhillusion.n2mix.helper.office.excel.reader.ExcelReader;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.RowData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;
import tech.nmhillusion.n2mix.util.ExceptionUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.IdConstant;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.CommodityEntity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-07
 */
public interface CommodityService {

    List<CommodityEntity> findAll();

    CommodityEntity sync(CommodityEntity commodityEntity);

    List<CommodityEntity> importExcelFile(MultipartFile excelFile);

    @TransactionalService
    class Impl implements CommodityService {
        private final CommodityRepository repository;
        private final SequenceService sequenceService;

        public Impl(CommodityRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public List<CommodityEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public CommodityEntity sync(CommodityEntity commodityEntity) {
            if (null == commodityEntity.getCreateTime()) {
                commodityEntity.setCreateTime(
                        ZonedDateTime.now()
                );
            }

            if (IdConstant.MIN_ID > commodityEntity.getComId()) {
                commodityEntity.setComId(
                        sequenceService.nextValue(
                                sequenceService.generateSeqNameForClass(
                                        getClass()
                                        , CommodityEntity.ID.COM_ID.name()
                                )
                        )
                );
            }

            return repository.save(commodityEntity);
        }

        @Override
        public List<CommodityEntity> importExcelFile(MultipartFile excelFile) {
            try {
                final List<SheetData> sheets = ExcelReader.read(excelFile.getInputStream());

                final List<CommodityEntity> totalCommodityList = new ArrayList<>();

                for (SheetData sheetData_ : sheets) {
                    totalCommodityList.addAll(
                            parseCommodityFromSheet(sheetData_)
                    );
                }

                repository.saveAllAndFlush(totalCommodityList);
                sequenceService.modifyCurrentValue(
                        sequenceService.generateSeqNameForClass(getClass(), CommodityEntity.ID.COM_ID.name())
                        , repository.getMaxId()
                );

                return totalCommodityList;
            } catch (Throwable ex) {
                throw ExceptionUtil.throwException(ex);
            }
        }

        private Collection<? extends CommodityEntity> parseCommodityFromSheet(SheetData sheetData) {
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

            if (!"comId".equalsIgnoreCase(cells_.get(0).getStringValue())
                    || !"comName".equalsIgnoreCase(cells_.get(1).getStringValue())
                    || !"comTypeId".equalsIgnoreCase(cells_.get(2).getStringValue())
                    || !"createTime".equalsIgnoreCase(cells_.get(3).getStringValue())
            ) {
                getLogger(this).warn("Sheet does not valid to import");
                return Collections.emptyList();
            }

            final List<CommodityEntity> constructItems = rows.stream()
                    .skip(1)
                    .map(row_ -> {
                        final List<CellData> cells = row_.getCells();
                        if (4 > cells.size()) {
                            throw new AppRuntimeException("Does not enough cell to construct a commodity: " + cells);
                        }

                        final String comId = cells.get(0).getStringValue();
                        final String comName = cells.get(1).getStringValue();
                        final String comTypeId = cells.get(2).getStringValue();
                        final String createTime = cells.get(3).getStringValue();

                        return new CommodityEntity()
                                .setComId((long) Double.parseDouble(comId))
                                .setComName(comName)
                                .setComTypeId((int) Double.parseDouble(comTypeId))
                                .setCreateTime(ZonedDateTime.parse(createTime));
                    })
                    .toList();

            getLogger(this)
                    .info("Import {} commodities from sheet: {}", constructItems.size(), sheetData.getSheetName());
            return constructItems;
        }
    }

}
