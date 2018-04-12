import { BaseEntity } from './../../shared';

export class Driver implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public cars?: BaseEntity[],
    ) {
    }
}
