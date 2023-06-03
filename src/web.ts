import { WebPlugin } from '@capacitor/core';

import type { PreviewVideoPlugin } from './definitions';

export class PreviewVideoWeb extends WebPlugin implements PreviewVideoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  createVideoPlayer(_options: any): Promise<any> {
    throw new Error('Method not implemented.');
  }
  destroyVideoPlayer(_options: any): Promise<any> {
    throw new Error('Method not implemented.');
  }
}
