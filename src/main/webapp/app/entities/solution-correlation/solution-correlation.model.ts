import { BaseEntity } from './../../shared';

export class SolutionCorrelation implements BaseEntity {
    constructor(
        public id?: number,
        public imgFileUrl?: string,
        public imgFileContentType?: string,
        public imgFileBase64Data?: any,
        public imgFileFileSource?: any,
        public imgFile?: any,
        public goLink?: any,
        public solution?: BaseEntity,
    ) {
    }
}
