import { BaseEntity } from './../../shared';

export class Car implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public owner?: BaseEntity,
        public drivers?: BaseEntity[],
    ) {
    }
}
