import { WebPlugin } from '@capacitor/core';

import type { PreviewVideoPlugin } from './definitions';

export class PreviewVideoWeb extends WebPlugin implements PreviewVideoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  previewStartFromRemote(options: { url: string }): Promise<void> {
    console.log(`previewStartFromRemote: options: ${options}`);
    throw new Error('Method not implemented.');
  }
  previewStartFromLocal(options: { path: string }): Promise<void> {
    console.log(`previewStartFromLocal: options: ${options}`);
    throw new Error('Method not implemented.');
  }
  previewStop(): Promise<void> {
    throw new Error('Method not implemented.');
  }
  playFullScreenFromRemote(options: { url: string }): Promise<void> {
    console.log(`playFullScreenFromRemote: options: ${options}`);
    throw new Error('Method not implemented.');
  }
  playFullScreenFromLocal(options: { path: string }): Promise<void> {
    console.log(`playFullScreenFromLocal: options: ${options}`);
    throw new Error('Method not implemented.');
  }
  stopFullScreen(): Promise<void> {
    throw new Error('Method not implemented.');
  }
}
