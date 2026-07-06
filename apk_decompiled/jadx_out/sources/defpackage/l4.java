package defpackage;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.RoundedCorner;
import android.view.ScrollCaptureCallback;
import android.view.ScrollCaptureSession;
import android.view.ScrollCaptureTarget;
import android.view.autofill.AutofillId;
import android.view.translation.TranslationRequestValue;
import android.view.translation.TranslationResponseValue;
import android.view.translation.ViewTranslationRequest;
import android.view.translation.ViewTranslationResponse;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class l4 {
    public static /* bridge */ /* synthetic */ void B(ViewTranslationRequest.Builder builder, TranslationRequestValue translationRequestValue) {
        builder.setValue("android:text", translationRequestValue);
    }

    public static /* bridge */ /* synthetic */ int C(RoundedCorner roundedCorner) {
        return roundedCorner.getRadius();
    }

    public static /* bridge */ /* synthetic */ int b(RoundedCorner roundedCorner) {
        return roundedCorner.getPosition();
    }

    public static /* bridge */ /* synthetic */ Point c(RoundedCorner roundedCorner) {
        return roundedCorner.getCenter();
    }

    public static /* bridge */ /* synthetic */ RoundedCorner j(Display display, int i) {
        return display.getRoundedCorner(i);
    }

    public static /* bridge */ /* synthetic */ ScrollCaptureSession k(Object obj) {
        return (ScrollCaptureSession) obj;
    }

    public static /* synthetic */ ScrollCaptureTarget l(b4 b4Var, Rect rect, Point point, ScrollCaptureCallback scrollCaptureCallback) {
        return new ScrollCaptureTarget(b4Var, rect, point, scrollCaptureCallback);
    }

    public static /* bridge */ /* synthetic */ TranslationRequestValue n(l7 l7Var) {
        return TranslationRequestValue.forText(l7Var);
    }

    public static /* bridge */ /* synthetic */ TranslationResponseValue o(ViewTranslationResponse viewTranslationResponse) {
        return viewTranslationResponse.getValue("android:text");
    }

    public static /* synthetic */ ViewTranslationRequest.Builder p(AutofillId autofillId, long j) {
        return new ViewTranslationRequest.Builder(autofillId, j);
    }

    public static /* bridge */ /* synthetic */ ViewTranslationRequest q(ViewTranslationRequest.Builder builder) {
        return builder.build();
    }

    public static /* bridge */ /* synthetic */ ViewTranslationResponse r(Object obj) {
        return (ViewTranslationResponse) obj;
    }

    public static /* bridge */ /* synthetic */ CharSequence s(TranslationResponseValue translationResponseValue) {
        return translationResponseValue.getText();
    }

    public static /* synthetic */ void t() {
    }

    public static /* bridge */ /* synthetic */ void y(ScrollCaptureTarget scrollCaptureTarget, Rect rect) {
        scrollCaptureTarget.setScrollBounds(rect);
    }
}
