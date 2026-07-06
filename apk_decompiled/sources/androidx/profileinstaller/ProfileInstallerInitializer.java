package androidx.profileinstaller;

import android.content.Context;
import android.os.Build;
import android.view.Choreographer;
import defpackage.n00;
import defpackage.rt;
import defpackage.yx;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class ProfileInstallerInitializer implements n00 {
    @Override // defpackage.n00
    public final List a() {
        return Collections.EMPTY_LIST;
    }

    @Override // defpackage.n00
    public final Object b(Context context) {
        if (Build.VERSION.SDK_INT < 24) {
            return new rt(22);
        }
        Choreographer.getInstance().postFrameCallback(new yx(this, context.getApplicationContext()));
        return new rt(22);
    }
}
