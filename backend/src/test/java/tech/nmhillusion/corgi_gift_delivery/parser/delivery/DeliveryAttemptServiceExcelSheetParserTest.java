package tech.nmhillusion.corgi_gift_delivery.parser.delivery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import tech.nmhillusion.corgi_gift_delivery.domains.delivery.DeliveryExcelSheetParser;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.n2mix.helper.office.excel.reader.ExcelReader;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;

import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-12
 */
class DeliveryAttemptServiceExcelSheetParserTest {
    private final Logger logger = LoggerFactory.getLogger(DeliveryAttemptServiceExcelSheetParserTest.class);

    @Test
    void parse() {
        final MockMultipartFile importFile = Assertions.assertDoesNotThrow(() -> new MockMultipartFile("importFile",
                        getClass().getClassLoader().getResourceAsStream("excels/deliveries.xlsx")
                )
        );

        Assertions.assertNotNull(importFile);

        final DeliveryExcelSheetParser deliveryExcelSheetParser = new DeliveryExcelSheetParser();

        final List<SheetData> sheetDataList = Assertions.assertDoesNotThrow(() -> ExcelReader.read(importFile.getInputStream()));

        Assertions.assertNotNull(sheetDataList);

        Assertions.assertFalse(sheetDataList.isEmpty());

        for (SheetData sheetData : sheetDataList) {
            final List<DeliveryEntity> deliveryEntities = Assertions.assertDoesNotThrow(() -> deliveryExcelSheetParser.parse(sheetData));

            Assertions.assertNotNull(deliveryEntities);

            logger.info("parsed deliveries = {}", deliveryEntities);
            logger.info("parsed deliveries size = {}", deliveryEntities.size());
        }
    }

}