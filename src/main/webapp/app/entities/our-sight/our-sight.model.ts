import { BaseEntity } from './../../shared';

export class OurSight implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public keyWord?: any,
        public imgFileUrl?: string,
        public imgFileContentType?: string,
        public imgFileBase64Data?: any,
        public imgFileFileSource?: any,
        public imgFile?: any,
    ) {
    }
}
