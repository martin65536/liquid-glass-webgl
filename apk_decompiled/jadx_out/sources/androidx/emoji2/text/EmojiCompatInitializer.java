package androidx.emoji2.text;

import android.content.Context;
import androidx.lifecycle.ProcessLifecycleInitializer;
import defpackage.hu;
import defpackage.j2;
import defpackage.j80;
import defpackage.l80;
import defpackage.n00;
import defpackage.oq;
import defpackage.pq;
import defpackage.r7;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class EmojiCompatInitializer implements n00 {
    @Override // defpackage.n00
    public final List a() {
        return Collections.singletonList(ProcessLifecycleInitializer.class);
    }

    @Override // defpackage.n00
    public final Object b(Context context) {
        hu huVar = new hu(new j2(context));
        huVar.b = 1;
        if (oq.k == null) {
            synchronized (oq.j) {
                try {
                    if (oq.k == null) {
                        oq.k = new oq(huVar);
                    }
                } finally {
                }
            }
        }
        c(context);
        return Boolean.TRUE;
    }

    public final void c(Context context) {
        Object obj;
        r7 t = r7.t(context);
        t.getClass();
        synchronized (r7.j) {
            try {
                obj = ((HashMap) t.f).get(ProcessLifecycleInitializer.class);
                if (obj == null) {
                    obj = t.o(ProcessLifecycleInitializer.class, new HashSet());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        l80 f = ((j80) obj).f();
        f.a(new pq(this, f));
    }
}
