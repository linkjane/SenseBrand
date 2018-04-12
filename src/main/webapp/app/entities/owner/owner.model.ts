import { BaseEntity } from './../../shared';

export class Owner implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public age?: number,
        public cars?: BaseEntity[],
    ) {
    }
}
