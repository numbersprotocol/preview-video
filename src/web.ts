import { WebPlugin } from '@capacitor/core';

import type { PreviewVideoPlugin } from './definitions';

export class PreviewVideoWeb extends WebPlugin implements PreviewVideoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  previewStartFromRemote(url: string): Promise<void> {
    console.log(`previewStartFromRemote.url: ${url}`);
    throw new Error('Method not implemented.');
  }
  previewStartFromLocal(path: string): Promise<void> {
    console.log(`previewStartFromLocal.path: ${path}`);
    throw new Error('Method not implemented.');
  }
  previewStop(): Promise<void> {
    throw new Error('Method not implemented.');
  }
  playFullScreenFromRemote(url: string): Promise<void> {
    console.log(`playFullScreenFromRemote.url: ${url}`);
    throw new Error('Method not implemented.');
  }
  playFullScreenFromLocal(path: string): Promise<void> {
    console.log(`playFullScreenFromLocal.path: ${path}`);
    throw new Error('Method not implemented.');
  }
  stopFullScreen(): Promise<void> {
    throw new Error('Method not implemented.');
  }
}
