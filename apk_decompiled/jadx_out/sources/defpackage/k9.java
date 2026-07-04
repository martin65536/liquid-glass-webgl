package defpackage;

import android.content.ContentProviderClient;
import android.content.res.TypedArray;
import android.drm.DrmManagerClient;
import android.media.MediaDrm;
import android.media.MediaMetadataRetriever;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k9 implements sn {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ k9(int i, Object obj, Object obj2) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
    }

    @Override // defpackage.sn
    public final void a() {
        int i = this.a;
        Object obj = this.c;
        Object obj2 = this.b;
        switch (i) {
            case 0:
                d9 d9Var = (d9) obj2;
                vg vgVar = (vg) obj;
                if (d9Var.a != null) {
                    vgVar.b.e();
                    return;
                }
                if (d9Var.b != null) {
                    c9 c9Var = vgVar.a;
                    ArrayList arrayList = (ArrayList) c9Var.a;
                    CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) c9Var.c;
                    Iterator it = copyOnWriteArrayList.iterator();
                    it.getClass();
                    while (it.hasNext()) {
                        AutoCloseable autoCloseable = (AutoCloseable) it.next();
                        if (autoCloseable instanceof AutoCloseable) {
                            autoCloseable.close();
                        } else if (autoCloseable instanceof ExecutorService) {
                            x0.x((ExecutorService) autoCloseable);
                        } else if (autoCloseable instanceof TypedArray) {
                            ((TypedArray) autoCloseable).recycle();
                        } else if (autoCloseable instanceof MediaMetadataRetriever) {
                            ((MediaMetadataRetriever) autoCloseable).release();
                        } else if (autoCloseable instanceof MediaDrm) {
                            ((MediaDrm) autoCloseable).release();
                        } else if (autoCloseable instanceof DrmManagerClient) {
                            ((DrmManagerClient) autoCloseable).release();
                        } else if (autoCloseable instanceof ContentProviderClient) {
                            ((ContentProviderClient) autoCloseable).release();
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                    copyOnWriteArrayList.clear();
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj3 = arrayList.get(i2);
                        i2++;
                        ((jh0) obj3).e();
                    }
                    arrayList.clear();
                    return;
                }
                v7.o("Unreachable");
                return;
            case 1:
                ((p70) obj2).g.k(obj);
                return;
            default:
                l71 l71Var = (l71) obj2;
                View view = (View) obj;
                int i3 = l71Var.t - 1;
                l71Var.t = i3;
                if (i3 == 0) {
                    int i4 = j51.a;
                    e51.b(view, null);
                    j51.a(view, null);
                    view.removeOnAttachStateChangeListener(l71Var.u);
                    return;
                }
                return;
        }
    }
}
