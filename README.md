# @numbersprotocol/preview-video

Preview Video using native iOS, Android players

## Install

```bash
npm install @numbersprotocol/preview-video
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`previewStartFromRemote(...)`](#previewstartfromremote)
* [`previewStartFromLocal(...)`](#previewstartfromlocal)
* [`previewStop()`](#previewstop)
* [`playFullScreenFromRemote(...)`](#playfullscreenfromremote)
* [`playFullScreenFromLocal(...)`](#playfullscreenfromlocal)
* [`stopFullScreen()`](#stopfullscreen)
* [`addListener('iosPlayerDismissed', ...)`](#addlisteneriosplayerdismissed)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### previewStartFromRemote(...)

```typescript
previewStartFromRemote(options: { url: string; }) => Promise<void>
```

Not implemented for iOS, Android no usage in our case for now

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ url: string; }</code> |

--------------------


### previewStartFromLocal(...)

```typescript
previewStartFromLocal(options: { path: string; }) => Promise<void>
```

Not implemented for iOS, Android no usage in our case for now

| Param         | Type                           |
| ------------- | ------------------------------ |
| **`options`** | <code>{ path: string; }</code> |

--------------------


### previewStop()

```typescript
previewStop() => Promise<void>
```

Not implemented for iOS, Android no usage in our case for now

--------------------


### playFullScreenFromRemote(...)

```typescript
playFullScreenFromRemote(options: { url: string; }) => Promise<void>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ url: string; }</code> |

--------------------


### playFullScreenFromLocal(...)

```typescript
playFullScreenFromLocal(options: { path: string; }) => Promise<void>
```

| Param         | Type                           |
| ------------- | ------------------------------ |
| **`options`** | <code>{ path: string; }</code> |

--------------------


### stopFullScreen()

```typescript
stopFullScreen() => Promise<void>
```

--------------------


### addListener('iosPlayerDismissed', ...)

```typescript
addListener(eventName: 'iosPlayerDismissed', listenerFunc: IOSPlayerDismissed) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                                              |
| ------------------ | ----------------------------------------------------------------- |
| **`eventName`**    | <code>'iosPlayerDismissed'</code>                                 |
| **`listenerFunc`** | <code><a href="#iosplayerdismissed">IOSPlayerDismissed</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### IOSPlayerDismissed

<code>(data: any): void</code>

</docgen-api>
