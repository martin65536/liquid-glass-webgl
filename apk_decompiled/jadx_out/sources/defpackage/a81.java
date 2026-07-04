package defpackage;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a81 extends ContentObserver {
    public final /* synthetic */ zb a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public a81(zb zbVar, Handler handler) {
        super(handler);
        this.a = zbVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        this.a.q(x31.a);
    }
}
