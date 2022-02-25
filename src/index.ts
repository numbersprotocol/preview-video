import { registerPlugin } from '@capacitor/core';

import type { PreviewVideoPlugin } from './definitions';

const PreviewVideo = registerPlugin<PreviewVideoPlugin>('PreviewVideo', {
  web: () => import('./web').then(m => new m.PreviewVideoWeb()),
});

export * from './definitions';
export { PreviewVideo };
