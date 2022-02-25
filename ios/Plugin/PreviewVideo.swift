import Foundation

@objc public class PreviewVideo: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
