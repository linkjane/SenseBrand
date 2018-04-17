import { BaseEntity } from './../../shared';

export class Test1 implements BaseEntity {
    constructor(
        public id?: number,
        public test?: string,
        public n?: string,
        public sdf?: string,
        public imageFileUrl?: string,
        public imageFileContentType?: string,
        public imageFileBase64Data?: any,
        public imageFileFileSource?: any,
        public imageFile?: any,
        public blobFileUrl?: string,
        public blobFileContentType?: string,
        public blobFileBase64Data?: any,
        public blobFileFileSource?: any,
        public blobFile?: any,
        public textFile?: string,
        public textFileTest?: any,
    ) {
    }
}
