import { WebPlugin } from '@capacitor/core';

import type { PreviewVideoPlugin } from './definitions';

export class PreviewVideoWeb extends WebPlugin implements PreviewVideoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async previewStartFromRemote(options: { url: string }): Promise<{ value: string }> {
    console.log(`previewStartFromRemote: options: ${options}`);
    return { value: 'Method not implemented.' };
  }
  async previewStartFromLocal(options: { path: string }): Promise<{ value: string }> {
    console.log(`previewStartFromLocal: options: ${options}`);
    return { value: 'Method not implemented.' };
  }
  async previewStop(): Promise<{ value: string }> {
    return { value: 'Method not implemented.' };
  }
  async playFullScreenFromRemote(options: { url: string }): Promise<{ value: string }> {
    console.log(`playFullScreenFromRemote: options: ${options}`);
    return { value: 'Method not implemented.' };
  }
  async playFullScreenFromLocal(options: { path: string }): Promise<{ value: string }> {
    console.log(`playFullScreenFromLocal: options: ${options}`);
    return { value: 'Method not implemented.' };
  }
  async stopFullScreen(): Promise<{ value: string }> {
    return { value: 'Method not implemented.' };
  }
}
