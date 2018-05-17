import { BaseEntity } from './../../shared';

export class BrandRegion implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public brands?: BaseEntity[],
    ) {
    }
}
