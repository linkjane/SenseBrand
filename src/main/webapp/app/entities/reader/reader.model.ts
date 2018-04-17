import { BaseEntity } from './../../shared';

export class Reader implements BaseEntity {
    constructor(
        public id?: number,
        public imageFile?: string,
        public textFile?: any,
        public blobFile?: string,
    ) {
    }
}
