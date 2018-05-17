import { BaseEntity } from './../../shared';

export class Brand implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public bannerImgFileUrl?: string,
        public bannerImgFileContentType?: string,
        public bannerImgFileBase64Data?: any,
        public bannerImgFileFileSource?: any,
        public bannerImgFile?: any,
        public profileImgFileUrl?: string,
        public profileImgFileContentType?: string,
        public profileImgFileBase64Data?: any,
        public profileImgFileFileSource?: any,
        public profileImgFile?: any,
        public introduction?: any,
        public logoUrl?: string,
        public logoContentType?: string,
        public logoBase64Data?: any,
        public logoFileSource?: any,
        public logo?: any,
        public establishTime?: any,
        public cradle?: any,
        public chairman?: any,
        public phoneNumber?: any,
        public officialWebsite?: any,
        public adPhrase?: any,
        public brandSubDetails?: BaseEntity[],
        public brandRanks?: BaseEntity[],
        public brandRegions?: BaseEntity[],
        public industrySecondClass?: BaseEntity,
    ) {
    }
}
