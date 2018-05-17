import { BaseEntity } from './../../shared';

export class BrandRank implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public brands?: BaseEntity[],
    ) {
    }
}
