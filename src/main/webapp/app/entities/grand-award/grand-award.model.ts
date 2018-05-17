import { BaseEntity } from './../../shared';

export class GrandAward implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public imgFileUrl?: string,
        public imgFileContentType?: string,
        public imgFileBase64Data?: any,
        public imgFileFileSource?: any,
        public imgFile?: any,
        public introduction?: any,
        public detailLink?: any,
    ) {
    }
}
