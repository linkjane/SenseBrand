import { BaseEntity } from './../../shared';

export class Person implements BaseEntity {
    constructor(
        public id?: number,
        public file1ContentType?: string,
        public file1?: any,
        public file2ContentType?: string,
        public file2?: any,
        public file3?: any,
    ) {
    }
}
