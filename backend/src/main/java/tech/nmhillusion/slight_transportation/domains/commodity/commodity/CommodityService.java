package tech.nmhillusion.slight_transportation.domains.commodity.commodity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.n2mix.exception.AppRuntimeException;
import tech.nmhillusion.n2mix.helper.office.excel.reader.ExcelReader;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.RowData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;
import tech.nmhillusion.n2mix.util.ExceptionUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.CommodityEntity;
import tech.nmhillusion.slight_transportation.helper.CollectionHelper;
import tech.nmhillusion.slight_transportation.util.NumberUtil;
import tech.nmhillusion.slight_transportation.validator.IdValidator;

import java.time.ZonedDateTime;
import java.util.*;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-07
 */
public interface CommodityService {

    CommodityEntity sync(CommodityEntity commodityEntity);

    List<CommodityEntity> importExcelFile(MultipartFile excelFile);

    CommodityEntity findById(long commodityId);

    Page<CommodityEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    @TransactionalService
    class Impl implements CommodityService {
        private final CommodityRepository repository;
        private final SequenceService sequenceService;

        public Impl(CommodityRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public Page<CommodityEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            final String keyword = CollectionHelper.getStringOrNullIfAbsent(dto, "keyword");

            return repository.search(
                    keyword,
                    PageRequest.of(pageIndex, pageSize)
            );
        }

        @Override
        public CommodityEntity sync(CommodityEntity commodityEntity) {
            if (null == commodityEntity.getCreateTime()) {
                commodityEntity.setCreateTime(
                        ZonedDateTime.now()
                );
            }

            if (IdValidator.isNotSetId(commodityEntity.getComId())) {
                commodityEntity.setComId(
                        sequenceService.nextValueInString(
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

        @Override
        public CommodityEntity findById(long commodityId) {
            return repository.findById(commodityId).orElse(null);
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
                                .setComId(NumberUtil.parseStringFromDoubleToLong(comId))
                                .setComName(comName)
                                .setComTypeId(NumberUtil.parseStringFromDoubleToLong(comTypeId))
                                .setCreateTime(ZonedDateTime.parse(createTime));
                    })
                    .toList();

            getLogger(this)
                    .info("Import {} commodities from sheet: {}", constructItems.size(), sheetData.getSheetName());
            return constructItems;
        }
    }

}
