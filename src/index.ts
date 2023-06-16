/* eslint-disable @typescript-eslint/no-namespace */
import { Capacitor, registerPlugin } from '@capacitor/core';

import type { CreatePreviewVideoArgs, PreviewVideoPlugin } from './definitions';

export const CapaciorPreviewVideo = registerPlugin<PreviewVideoPlugin>(
  'PreviewVideo',
  {
    web: () => import('./web').then(m => new m.PreviewVideoWeb()),
  },
);

export * from './definitions';

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

// export interface PreviewVideoInterface {}

export class PreviewVideo {
  private id: string;
  private element: HTMLElement | null = null;

  constructor(id: string) {
    this.id = id;
  }

  public static async create(
    options: CreatePreviewVideoArgs,
  ): Promise<PreviewVideo> {
    const newPreviewVideo = new PreviewVideo(options.id);

    if (!options.element) {
      throw new Error('container element is required');
    }

    newPreviewVideo.element = options.element;

    const elementBounds = options.element.getBoundingClientRect();
    options.config.width = elementBounds.width;
    options.config.height = elementBounds.height;
    options.config.x = elementBounds.x;
    options.config.y = elementBounds.y;

    if (Capacitor.getPlatform() == 'android') {
      (options.element as any) = {};
      newPreviewVideo.initScrolling();
    }

    if (Capacitor.getPlatform() == 'ios') {
      (options.element as any) = {};
    }

    await CapaciorPreviewVideo.create(options);

    return newPreviewVideo;
  }

  initScrolling(): void {
    const ionContents = document.getElementsByTagName('ion-content');

    // eslint-disable-next-line @typescript-eslint/prefer-for-of
    for (let i = 0; i < ionContents.length; i++) {
      (ionContents[i] as any).scrollEvents = true;
    }

    window.addEventListener('ionScroll', this.handleScrollEvent);
    window.addEventListener('scroll', this.handleScrollEvent);
    window.addEventListener('resize', this.handleScrollEvent);
    if (screen.orientation) {
      screen.orientation.addEventListener('change', () => {
        setTimeout(this.updatePreviewVideoBounds, 500);
      });
    } else {
      window.addEventListener('orientationchange', () => {
        setTimeout(this.updatePreviewVideoBounds, 500);
      });
    }
  }

  disableScrolling(): void {
    window.removeEventListener('ionScroll', this.handleScrollEvent);
    window.removeEventListener('scroll', this.handleScrollEvent);
    window.removeEventListener('resize', this.handleScrollEvent);
    if (screen.orientation) {
      screen.orientation.removeEventListener('change', () => {
        setTimeout(this.updatePreviewVideoBounds, 1000);
      });
    } else {
      window.removeEventListener('orientationchange', () => {
        setTimeout(this.updatePreviewVideoBounds, 1000);
      });
    }
  }

  handleScrollEvent = (): void => this.updatePreviewVideoBounds();

  private updatePreviewVideoBounds(): void {
    if (this.element) {
      const mapRect = this.element.getBoundingClientRect();

      CapaciorPreviewVideo.onScroll({
        id: this.id,
        previewVideoBounds: {
          x: mapRect.x,
          y: mapRect.y,
          width: mapRect.width,
          height: mapRect.height,
        },
      });
    }
  }

  async destroy(): Promise<void> {
    if (Capacitor.getPlatform() == 'android') {
      this.disableScrolling();
    }

    return CapaciorPreviewVideo.destroy({
      id: this.id,
    });
  }
}
