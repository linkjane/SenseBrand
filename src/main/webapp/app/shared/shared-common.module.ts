import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/zh-Hans';

import { WindowRef } from './tracker/window.service';
import {
    SenseBrandSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';
import { WysiwygComponent } from './wysiwyg/wysiwyg.component';
import { FroalaEditorModule, FroalaViewModule } from 'angular-froala-wysiwyg';

@NgModule({
    imports: [
        SenseBrandSharedLibsModule,
        FroalaEditorModule,
        FroalaViewModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        WysiwygComponent
    ],
    providers: [
        JhiLanguageHelper,
        WindowRef,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'zh-Hans'
        },
    ],
    exports: [
        SenseBrandSharedLibsModule,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        WysiwygComponent

    ]
})
export class SenseBrandSharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
