package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import org.springframework.stereotype.Component;
import tech.nmhillusion.corgi_gift_delivery.constant.FormatConstant;
import tech.nmhillusion.corgi_gift_delivery.domains.delivery.DeliveryService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus.DeliveryStatusService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryType.DeliveryTypeService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryAttemptEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryStatusEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryTypeEntity;
import tech.nmhillusion.corgi_gift_delivery.parser.ExcelSheetParser;
import tech.nmhillusion.corgi_gift_delivery.parser.RowIdxMapping;
import tech.nmhillusion.corgi_gift_delivery.util.NumberUtil;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.RowData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;
import tech.nmhillusion.n2mix.util.DateUtil;
import tech.nmhillusion.n2mix.validator.StringValidator;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */

@Component
public class DeliveryAttemptExcelSheetParser extends ExcelSheetParser<DeliveryAttemptEntity> {
    private final DeliveryService deliveryService;
    private final DeliveryTypeService deliveryTypeService;
    private final DeliveryStatusService deliveryStatusService;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FormatConstant.DATE_FORMAT.getFormatValue());

    public DeliveryAttemptExcelSheetParser(DeliveryService deliveryService, DeliveryTypeService deliveryTypeService, DeliveryStatusService deliveryStatusService) {
        this.deliveryService = deliveryService;
        this.deliveryTypeService = deliveryTypeService;
        this.deliveryStatusService = deliveryStatusService;
    }

    @Override
    public List<DeliveryAttemptEntity> parse(SheetData sheetData) throws NotFoundException {
        final List<RowData> dataRows = sheetData.getRows();
        final List<DeliveryAttemptEntity> resultList = new ArrayList<>();

        List<RowIdxMapping> rowIdxMappings = null;

        for (RowData dataRow : dataRows) {
            final List<CellData> dataRowCells = dataRow.getCells();

            if (dataRowCells.size() < DeliveryAttemptParserEnum.values().length) {
                continue;
            }

            if (null == rowIdxMappings) {
                rowIdxMappings = mappingIndicesForColumns(
                        dataRowCells
                        , Arrays.stream(DeliveryAttemptParserEnum.values())
                                .map(DeliveryAttemptParserEnum::getColumnName)
                                .toList()
                );

                continue;
            }

            final String eventId = getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryAttemptParserEnum.EVENT_ID.getColumnName());
            final String customerId = NumberUtil.parseStringFromDoubleToLong(
                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryAttemptParserEnum.CUSTOMER_ID.getColumnName())
            );

            if (StringValidator.isBlank(eventId) || StringValidator.isBlank(customerId)) {
                LogHelper.getLogger(this).error("this error is empty of key. {}", dataRow);
                continue;
            }

            final Long deliveryId = deliveryService.getDeliveryIdByEventAndCustomer(eventId, customerId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            MessageFormat.format("Cannot find delivery with eventId = {0}, customerId = {1}"
                                    , eventId
                                    , customerId
                            )
                    ));

            final String deliveryType = getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryAttemptParserEnum.DELIVERY_TYPE.getColumnName());
            final String deliveryStatus = getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryAttemptParserEnum.DELIVERY_STATUS.getColumnName());

            final String deliveryDateRaw = getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryAttemptParserEnum.DELIVERY_DATE.getColumnName());
            ZonedDateTime deliveryDate = null;
            if (!StringValidator.isBlank(deliveryDateRaw)) {
                final boolean isNumber = tech.nmhillusion.n2mix.util.NumberUtil.isNumber(deliveryDateRaw);

                if (isNumber) {
                    deliveryDate = DateUtil.convertToZonedDateTime(
                            org.apache.poi.ss.usermodel.DateUtil.getJavaDate(
                                    Double.parseDouble(deliveryDateRaw)
                            )
                    );
                } else {
                    final LocalDate localDate = LocalDate.parse(deliveryDateRaw, dateTimeFormatter);
                    deliveryDate = localDate.atStartOfDay(ZoneId.systemDefault());
                }
            }

            resultList.add(
                    new DeliveryAttemptEntity()
                            .setDeliveryId(
                                    deliveryId
                            )
                            .setDeliveryTypeId(
                                    Integer.parseInt(
                                            deliveryTypeService.getDeliveryTypeByTypeName(deliveryType)
                                                    .map(DeliveryTypeEntity::getTypeId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Invalid delivery type: " + deliveryType))
                                    )
                            )
                            .setDeliveryStatusId(
                                    Integer.parseInt(
                                            deliveryStatusService.getDeliveryStatusByStatusName(deliveryStatus)
                                                    .map(DeliveryStatusEntity::getStatusId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Invalid delivery status: " + deliveryStatus))
                                    )
                            )
                            .setDeliveryDate(
                                    deliveryDate
                            )
                            .setNote(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryAttemptParserEnum.NOTE.getColumnName())
                            )
            )
            ;
        }

        return resultList;
    }

}
