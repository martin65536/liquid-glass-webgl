package defpackage;

import android.content.Intent;
import android.content.IntentSender;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class zf implements Runnable {
    public final /* synthetic */ int e;
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;

    public /* synthetic */ zf(int i, int i2, Object obj, Object obj2) {
        this.e = i2;
        this.g = obj;
        this.f = i;
        this.h = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.e;
        Object obj = this.h;
        int i2 = this.f;
        Object obj2 = this.g;
        switch (i) {
            case 0:
                ((ag) obj2).a(i2, 0, new Intent().setAction("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST").putExtra("androidx.activity.result.contract.extra.SEND_INTENT_EXCEPTION", (IntentSender.SendIntentException) obj));
                return;
            default:
                ((zm) obj2).b.e(i2, obj);
                return;
        }
    }
}
