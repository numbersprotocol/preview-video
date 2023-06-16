import { WebPlugin } from '@capacitor/core';

import type {
  CreatePreviewVideoArgs,
  DestroyPreviewVideoArgs,
  OnScrollArgs,
  PreviewVideoPlugin,
} from './definitions';

export class PreviewVideoWeb extends WebPlugin implements PreviewVideoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  create(_args: CreatePreviewVideoArgs): Promise<any> {
    throw new Error('Method not implemented.');
  }
  destroy(_args: DestroyPreviewVideoArgs): Promise<any> {
    throw new Error('Method not implemented.');
  }
  onScroll(_args: OnScrollArgs): Promise<void> {
    throw new Error('Method not implemented.');
  }
}
