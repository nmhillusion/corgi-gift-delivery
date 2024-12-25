package tech.nmhillusion.slight_transportation.domains.commodity.commodityType;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.n2mix.helper.office.excel.writer.ExcelWriteHelper;
import tech.nmhillusion.n2mix.helper.office.excel.writer.model.BasicExcelDataModel;
import tech.nmhillusion.n2mix.util.ExceptionUtil;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.n2mix.validator.StringValidator;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.CommodityTypeEntity;

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

        public Impl(CommodityTypeRepository repository) {
            this.repository = repository;
        }

        @Override
        public List<CommodityTypeEntity> findAll() {
            return repository.findAll(
                    Sort.by(Sort.Order.asc("typeId"))
            );
        }

        @Transactional
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
            }

            return repository.save(entity);
        }

        @Override
        public List<CommodityTypeEntity> importExcel(MultipartFile excelFile) {
            getLogger(this)
                    .info("excelFile: {}", excelFile);


            return List.of();
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
    }
}
