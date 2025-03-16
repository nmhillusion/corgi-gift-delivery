import { IdType } from "../core/id.model";
import { SelectableModel } from "../core/selectable.model";

export interface CommodityTypeModel {
  typeName?: string;
  typeId?: IdType;
}

export interface CommodityTypeSelectableModel
  extends CommodityTypeModel,
    SelectableModel {}
