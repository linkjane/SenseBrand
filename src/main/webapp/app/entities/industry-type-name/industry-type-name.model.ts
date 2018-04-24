import { BaseEntity } from './../../shared';

export class IndustryTypeName implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public introduction?: any,
        public iconFileUrl?: string,
        public iconFileContentType?: string,
        public iconFileBase64Data?: any,
        public iconFileFileSource?: any,
        public iconFile?: any,
        public industryType?: BaseEntity,
    ) {
    }
}
