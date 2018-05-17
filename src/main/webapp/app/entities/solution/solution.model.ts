import { BaseEntity } from './../../shared';

export class Solution implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public introduction?: any,
        public bannerImgFileUrl?: string,
        public bannerImgFileContentType?: string,
        public bannerImgFileBase64Data?: any,
        public bannerImgFileFileSource?: any,
        public bannerImgFile?: any,
        public bannerIntroduction?: any,
        public summarizeTitle?: any,
        public summarizeImgFileUrl?: string,
        public summarizeImgFileContentType?: string,
        public summarizeImgFileBase64Data?: any,
        public summarizeImgFileFileSource?: any,
        public summarizeImgFile?: any,
        public summarizeContent?: any,
        public significanceTitle?: any,
        public significanceImgFileUrl?: string,
        public significanceImgFileContentType?: string,
        public significanceImgFileBase64Data?: any,
        public significanceImgFileFileSource?: any,
        public significanceImgFile?: any,
        public significanceIntroduction?: any,
        public detailAnalysisTitle?: any,
        public isShow?: boolean,
        public detailImgs?: BaseEntity[],
        public detailAnalyses?: BaseEntity[],
        public correlations?: BaseEntity[],
    ) {
        this.isShow = false;
    }
}
