import AVKit
import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(PreviewVideoPlugin)
public class PreviewVideoPlugin: CAPPlugin, UIViewControllerTransitioningDelegate {
    private let implementation = PreviewVideo()

    private var avPlayerController: AVPlayerViewController?

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }

    @objc func playFullScreenFromRemote(_ call: CAPPluginCall) {
        guard let urlParam = call.getString("url") else {
            call.reject("url is missing")
            return
        }
        guard let url = URL(string: urlParam) else {
            call.reject("url is invalid")
            return
        }

        DispatchQueue.main.async {
            self.avPlayerController = AVPlayerViewController()
            self.avPlayerController?.player = AVPlayer(url: url)
            self.avPlayerController?.player?.play()
            if let viewController = self.avPlayerController {
                self.bridge?.viewController?.present(viewController, animated: true, completion: nil)
                viewController.transitioningDelegate = self

                do {
                    try AVAudioSession.sharedInstance().setCategory(AVAudioSession.Category.playback, mode: AVAudioSession.Mode.default, options: [])
                } catch {
                    print("Setting category to AVAudioSessionCategoryPlayback failed.")
                }
            }
            call.resolve()
        }
    }

    @objc func stopFullScreen(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            self.avPlayerController?.player?.seek(to: CMTime.zero)
            self.avPlayerController?.player?.pause()
            self.avPlayerController?.dismiss(animated: true)
            // self.bridge?.viewController?.dismiss(animated: true, completion: nil)
            call.resolve()
        }
    }

    @objc func iosPlayerDismissed() {
        self.notifyListeners("iosPlayerDismissed", data: [:])
    }

    // MARK: UIViewControllerTransitioningDelegate
    // Gets called when a modal was dismissed
    public func animationController(forDismissed dismissed: UIViewController) -> UIViewControllerAnimatedTransitioning? {
        self.iosPlayerDismissed()

        return nil
    }
}

extension Notification.Name {
    static let kAVPlayerViewControllerDismissingNotification = Notification.Name.init("avPlayerDismissed")
}
