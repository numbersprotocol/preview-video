/* eslint-disable @typescript-eslint/no-namespace */
import { registerPlugin } from '@capacitor/core';

import type { PreviewVideoPlugin } from './definitions';

const PreviewVideo = registerPlugin<PreviewVideoPlugin>('PreviewVideo', {
  web: () => import('./web').then(m => new m.PreviewVideoWeb()),
});

export * from './definitions';
export { PreviewVideo };

class PreviewVideoCustomElement extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    this.style.overflow = 'scroll';
    (this.style as any)['-webkit-overflow-scrolling'] = 'touch';

    const overflowDiv = document.createElement('div');
    overflowDiv.style.height = '200%';

    this.appendChild(overflowDiv);
  }
}

customElements.define('preview-video', PreviewVideoCustomElement);

/**
 * Declare the type definition for a custom element called 'preview-video' within the JSX namespace.
 * It is typically used in TypeScript projects when we want to use a custom element in our JSX/TSX code.
 *
 * In TypeScript, the JSX namespace defines the types and interfaces for JSX elements and components.
 * By extending the IntrinsicElements interface, we can add custom elements and specify their props and attributes.
 *
 * In this specific example, the code declares that the 'preview-video' element can accept any props.
 * By doing this, we're telling TypeScript that this custom element can receive any valid HTML attributes
 * and that TypeScript should not raise any errors for unrecognized attributes on this element.
 */
declare global {
  export namespace JSX {
    export interface IntrinsicElements {
      'preview-video': any;
    }
  }
}
