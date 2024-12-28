package tech.nmhillusion.slight_transportation.domains.recipient.recipient;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */
public interface RecipientService {
    RecipientEntity sync(RecipientEntity recipientEntity);

    RecipientEntity findById(String id);

    Page<RecipientEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    void deleteById(String customerId);

    List<RecipientEntity> importExcelFile(MultipartFile excelFile);

    @TransactionalService
    class Impl implements RecipientService {

        private final RecipientRepository repository;
        private final SequenceService sequenceService;

        public Impl(RecipientRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public RecipientEntity sync(RecipientEntity recipientEntity) {
            final RecipientEntity savedEntity = repository.save(recipientEntity);

            if (IdConstant.MIN_ID > savedEntity.getRecipientId()) {
                savedEntity.setRecipientId(
                        sequenceService.nextValue(
                                sequenceService.generateSeqNameForClass(
                                        getClass()
                                        , RecipientEntity.ID.RECIPIENT_ID.name()
                                )
                        )
                );
            }

            LogHelper.getLogger(this).info("savedEntity: {}", savedEntity);
            return savedEntity;
        }

        @Override
        public RecipientEntity findById(String id) {
            return repository.findById(Long.parseLong(id)).orElse(null);
        }

        @Override
        public Page<RecipientEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            final String name = StringUtil.trimWithNull(dto.get("name"));

            LogHelper.getLogger(this).info("name: {}", name);
            return repository.search(
                    StringUtil.trimWithNull(name),
                    PageRequest.of(pageIndex, pageSize)
            );
        }

        @Override
        public void deleteById(String customerId) {
            repository.deleteById(Long.parseLong(customerId));
        }

        @Override
        public List<RecipientEntity> importExcelFile(MultipartFile excelFile) {
            try {
                final List<SheetData> sheets = ExcelReader.read(excelFile.getInputStream());

                final List<RecipientEntity> totalRecipientList = new ArrayList<>();

                for (SheetData sheetData_ : sheets) {
                    final List<RecipientEntity> recipientList = parseRecipientFromSheet(sheetData_);
                    totalRecipientList.addAll(recipientList);
                }

                repository.saveAllAndFlush(totalRecipientList);
                moveToMaxRecipientIdForSequence();

                return totalRecipientList;
            } catch (Throwable ex) {
                throw ExceptionUtil.throwException(ex);
            }
        }

        private List<RecipientEntity> parseRecipientFromSheet(SheetData sheetData) {
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

            if (!"recipientId".equalsIgnoreCase(cells_.get(0).getStringValue())
                    || !"fullName".equalsIgnoreCase(cells_.get(1).getStringValue())
                    || !"idCardNumber".equalsIgnoreCase(cells_.get(2).getStringValue())
                    || !"recipientTypeId".equalsIgnoreCase(cells_.get(3).getStringValue())
            ) {
                LogHelper.getLogger(this).warn("Sheet does not valid to import");
                return Collections.emptyList();
            }

            final List<RecipientEntity> constructItems = rows.stream()
                    .skip(1)
                    .map(row_ -> {
                        final List<CellData> cells = row_.getCells();
                        if (2 > cells.size()) {
                            throw new AppRuntimeException("Does not enough cell to construct a recipient: " + cells);
                        }

                        final String recipientId = cells.get(0).getStringValue();
                        final String recipientName = cells.get(1).getStringValue();
                        final String idCardNumber = cells.get(2).getStringValue();
                        final String recipientTypeId = cells.get(3).getStringValue();

                        return new RecipientEntity()
                                .setRecipientId((int) Double.parseDouble(recipientId))
                                .setFullName(recipientName)
                                .setIdCardNumber(idCardNumber)
                                .setRecipientTypeId(
                                        (int) Double.parseDouble(recipientTypeId)
                                )
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
    }
}
