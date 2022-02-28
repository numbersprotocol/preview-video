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

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ url: string; }</code> |

--------------------


### previewStartFromLocal(...)

```typescript
previewStartFromLocal(options: { path: string; }) => Promise<void>
```

| Param         | Type                           |
| ------------- | ------------------------------ |
| **`options`** | <code>{ path: string; }</code> |

--------------------


### previewStop()

```typescript
previewStop() => Promise<void>
```

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

</docgen-api>
