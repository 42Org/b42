# B42
Keyboard Driven browser, Inspired by Pentadactyl.

> Pentadactyl is dead! long live the Pentadactyl.

## How to Run
```
npm install shadow-cljs -g
shadow-cljs run tools.muon/fetch
npm install

npm run dev
npm run b42
```

## Release
```
npm run build
electron-packager . HelloWorld --platform=darwin --arch=x64 --version=1.4.13
```
