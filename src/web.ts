import { WebPlugin } from '@capacitor/core';

import type { PreviewVideoPlugin } from './definitions';

export class PreviewVideoWeb extends WebPlugin implements PreviewVideoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  openVideoPlayer(_options: any): Promise<any> {
    throw new Error('Method not implemented.');
  }
  closeVideoPlayer(_options: any): Promise<any> {
    throw new Error('Method not implemented.');
  }
}
