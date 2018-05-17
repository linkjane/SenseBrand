import { BaseEntity } from './../../shared';

export class SolutionDetailAnalysis implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public introduction?: any,
        public detailAnalysisImgs?: BaseEntity[],
        public solution?: BaseEntity,
    ) {
    }
}
