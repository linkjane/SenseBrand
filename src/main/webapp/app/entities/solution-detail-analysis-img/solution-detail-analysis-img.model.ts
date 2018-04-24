import { BaseEntity } from './../../shared';

export class SolutionDetailAnalysisImg implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public imgFilUrl?: string,
        public imgFilContentType?: string,
        public imgFilBase64Data?: any,
        public imgFilFileSource?: any,
        public imgFil?: any,
        public introduction?: any,
        public solutionDetailAnalysis?: BaseEntity,
    ) {
    }
}
