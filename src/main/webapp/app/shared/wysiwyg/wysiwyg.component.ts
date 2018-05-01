import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { STATIC_SERVER_URL, FILE_UPLOAD_URL } from '../../app.constants';

@Component({
  selector: 'jhi-wysiwyg',
  templateUrl: './wysiwyg.component.html',
  styles: []
})
export class WysiwygComponent implements OnInit {

    private _editorContent: string;
    @Input() set editorContent(editorContent: string) {
        console.log(editorContent);
        this._editorContent = editorContent;
        this.editorContentChange.emit(this._editorContent);
    }

    get editorContent() {
        return this._editorContent;
    }

    @Output()
    public editorContentChange: EventEmitter<string> = new EventEmitter<string>();

    private uploadParams: object = {
        key: 'wysiwyg',
        ssUrl: STATIC_SERVER_URL + '/'
    };

    private _options: object = {
        placeholderText: 'Edit Your Content Here!',
        language: 'zh_cn',
        theme: 'dark',
        editorClass: 'editor-class',
        fileUploadURL: FILE_UPLOAD_URL,
        imageUploadURL: FILE_UPLOAD_URL,
        videoUploadURL: FILE_UPLOAD_URL,
        fileUploadParams: this.uploadParams,
        imageUploadParams: this.uploadParams,
        videoUploadParams: this.uploadParams,
        height: 300,
        // maxsize is 20MB
        fileMaxSize: 20 * 1024 * 1024,
        events : {
        }
    };

    @Input() set options(options: object) {
        this._options = Object.assign(this._options, options);
    }

    get options() {
        return this._options;
    }

  constructor() { }

  ngOnInit() {
  }

}
