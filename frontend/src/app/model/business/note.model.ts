import { IdType } from "../core/id.model";

export interface NoteModel {
  noteId?: string;
  noteContent?: string;
  noteTime?: Date;
  recipientId?: IdType;
  deliveryId?: IdType;
  deliveryAttemptId?: IdType;
  importId?: IdType;
  warehouseItemId?: IdType;
}

export interface NoteOwnerDto {
  recipientId?: IdType;
  deliveryId?: IdType;
  deliveryAttemptId?: IdType;
  importId?: IdType;
  warehouseItemId?: IdType;
}
