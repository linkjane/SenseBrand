import { BaseEntity } from './../../shared';

export class DesignerShow implements BaseEntity {
    constructor(
        public id?: number,
        public firstLevelTitle?: any,
        public secondLevelTitle?: any,
        public introduction?: any,
        public designer?: BaseEntity,
    ) {
    }
}
