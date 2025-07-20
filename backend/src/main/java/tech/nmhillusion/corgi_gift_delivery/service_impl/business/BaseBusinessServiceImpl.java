package tech.nmhillusion.corgi_gift_delivery.service_impl.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.BaseBusinessEntity;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseBusinessService;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.corgi_gift_delivery.validator.IdValidator;
import tech.nmhillusion.n2mix.helper.office.excel.reader.ExcelReader;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;
import tech.nmhillusion.n2mix.type.function.ThrowableFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public abstract class BaseBusinessServiceImpl<E extends BaseBusinessEntity<Long>, R extends JpaRepository<E, Long>>
        implements BaseBusinessService<E> {
    protected final R repository;
    private final SequenceService sequenceService;

    protected BaseBusinessServiceImpl(R repository, SequenceService sequenceService) {
        this.repository = repository;
        this.sequenceService = sequenceService;
    }

    @Override
    public List<E> saveBatch(Iterable<E> entities) {
        if (entities == null) return null;

        for (E entity_ : entities) {
            if (IdValidator.isNotSetId(entity_.getId())) {
                entity_.setId(
                        sequenceService.getNextValue(getClass())
                );
            }
        }

        return repository.saveAll(
                entities
        );
    }

    protected List<E> parseExcelFileToEntityList(MultipartFile excelFile, ThrowableFunction<SheetData, List<E>> parser) throws Throwable {
        final List<SheetData> sheetList = ExcelReader.read(excelFile.getInputStream());
        final List<E> combinedList = new ArrayList<>();

        for (SheetData sheetData : sheetList) {
            final List<E> deliveryEntities = parser.throwableApply(sheetData);
            combinedList.addAll(deliveryEntities);
        }
        return combinedList;
    }
}
