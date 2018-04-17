import { BaseEntity } from './../../shared';

export class ReaderOld implements BaseEntity {
    constructor(
        public id?: number,
        public imageFileContentType?: string,
        public imageFile?: any,
        public textFile?: any,
        public blobFileContentType?: string,
        public blobFile?: any,
        public localTime?: any,
    ) {
    }
}
