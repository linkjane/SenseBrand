import { BaseEntity } from './../../shared';

export class DesignerSentiment implements BaseEntity {
    constructor(
        public id?: number,
        public firstLevelTitle?: any,
        public secondLevelTitle?: any,
        public imgFileUrl?: string,
        public imgFileContentType?: string,
        public imgFileBase64Data?: any,
        public imgFileFileSource?: any,
        public imgFile?: any,
        public designer?: BaseEntity,
    ) {
    }
}
