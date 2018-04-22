import { BaseEntity } from './../../shared';

export class DesignerIdeaDetails implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public shareTime?: any,
        public secondLevelTitle?: any,
        public introduction?: any,
        public designer?: BaseEntity,
    ) {
    }
}
