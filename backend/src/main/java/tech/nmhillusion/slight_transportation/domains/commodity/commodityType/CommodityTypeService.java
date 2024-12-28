package tech.nmhillusion.slight_transportation.domains.commodity.commodityType;

import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.n2mix.exception.AppRuntimeException;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.helper.office.excel.reader.ExcelReader;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.RowData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;
import tech.nmhillusion.n2mix.helper.office.excel.writer.ExcelWriteHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.model.BasicExcelDataModel;
import tech.nmhillusion.n2mix.util.ExceptionUtil;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.n2mix.validator.StringValidator;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.CommodityTypeEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static tech.nmhillusion.n2mix.helper.log.LogHelper.getLogger;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-23
 */
public interface CommodityTypeService {
    List<CommodityTypeEntity> findAll();

    CommodityTypeEntity sync(Map<String, ?> dto);

    List<CommodityTypeEntity> importExcel(MultipartFile excelFile);

    byte[] exportExcel();

    @TransactionalService
    class Impl implements CommodityTypeService {
        private final CommodityTypeRepository repository;
        private final SequenceService sequenceService;

        public Impl(CommodityTypeRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public List<CommodityTypeEntity> findAll() {
            return repository.findAll(
                    Sort.by(Sort.Order.asc("typeId"))
            );
        }

        @Override
        public CommodityTypeEntity sync(Map<String, ?> dto) {
            final String currentTypeId = StringUtil.trimWithNull(dto.get("typeId"));
            final String typeName = StringUtil.trimWithNull(dto.get("typeName"));

            final CommodityTypeEntity entity = new CommodityTypeEntity()
                    .setTypeName(typeName);

            getLogger(this).info("currentTypeId: {}", currentTypeId);
            if (!StringValidator.isBlank(currentTypeId)) {
                /// Mark: For update
                entity.setTypeId(Integer.parseInt(currentTypeId));
            } else {
                entity.setTypeId(
                        (int) sequenceService.nextValue(
                                sequenceService.generateSeqNameForClass(
                                        getClass()
                                        , CommodityTypeEntity.ID.TYPE_ID.name()
                                )
                        )
                );
            }

            return repository.save(entity);
        }

        private List<CommodityTypeEntity> parseCommodityTypeFromSheet(SheetData sheetData) {
            final List<RowData> rows = sheetData.getRows();
            if (null == rows) {
                throw new IllegalArgumentException("Rows is null for this sheet: " + sheetData.getSheetName());
            }

            final RowData firstRow = rows.getFirst();
            final List<CellData> cells_ = firstRow.getCells();

            if (!"typeId".equalsIgnoreCase(cells_.get(0).getStringValue())
                    || !"typeName".equalsIgnoreCase(cells_.get(1).getStringValue())
            ) {
                getLogger(this).warn("Sheet does not valid to import");
                return Collections.emptyList();
            }

            final List<CommodityTypeEntity> constructItems = rows.stream()
                    .skip(1)
                    .map(row_ -> {
                        final List<CellData> cells = row_.getCells();
                        if (2 > cells.size()) {
                            throw new AppRuntimeException("Does not enough cell to construct a commodity type: " + cells);
                        }

                        final String typeId = cells.get(0).getStringValue();
                        final String typeName = cells.get(1).getStringValue();

                        return new CommodityTypeEntity()
                                .setTypeId((int) Double.parseDouble(typeId))
                                .setTypeName(typeName);
                    })
                    .toList();

            LogHelper.getLogger(this).info("construct items = {}", constructItems);

            return constructItems;

        }

        @Override
        public List<CommodityTypeEntity> importExcel(MultipartFile excelFile) {
            getLogger(this)
                    .info("excelFile: {}", excelFile.getName());

            try {
                final List<SheetData> sheetList = ExcelReader.read(excelFile.getInputStream());

                final List<CommodityTypeEntity> totalTypeList = new ArrayList<>();

                for (SheetData sheetData_ : sheetList) {
                    getLogger(this)
                            .debug("data = {}", sheetData_);

                    totalTypeList.addAll(
                            parseCommodityTypeFromSheet(sheetData_)
                    );
                }

                repository.saveAllAndFlush(totalTypeList);
                moveToMaxTypeIdForSequence();

                return totalTypeList;
            } catch (Throwable ex) {
                throw ExceptionUtil.throwException(ex);
            }
        }

        @Override
        public byte[] exportExcel() {
            try {
                final List<CommodityTypeEntity> commodityTypeList = findAll();
                return new ExcelWriteHelper()
                        .addSheetData(
                                new BasicExcelDataModel()
                                        .setSheetName("CommodityType")
                                        .setHeaders(List.of(
                                                List.of("typeId", "typeName")
                                        ))
                                        .setBodyData(
                                                commodityTypeList
                                                        .stream()
                                                        .map(it -> List.of(
                                                                        StringUtil.trimWithNull(it.getTypeId())
                                                                        , it.getTypeName()
                                                                )
                                                        ).toList()
                                        )
                        )
                        .build()
                        ;
            } catch (Exception e) {
                throw ExceptionUtil.throwException(e);
            }
        }

        private void moveToMaxTypeIdForSequence() {
            final int currentMaxId = repository.getMaxTypeId();
            sequenceService.modifyCurrentValue(
                    sequenceService.generateSeqNameForClass(getClass(), CommodityTypeEntity.ID.TYPE_ID.name())
                    , currentMaxId
            );
        }
    }
}
