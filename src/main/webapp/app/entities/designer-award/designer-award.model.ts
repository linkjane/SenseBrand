import { BaseEntity } from './../../shared';

export class DesignerAward implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public introduction?: any,
        public imgFileUrl?: string,
        public imgFileContentType?: string,
        public imgFileBase64Data?: any,
        public imgFileFileSource?: any,
        public imgFile?: any,
        public detailLink?: any,
        public designer?: BaseEntity,
    ) {
    }
}
