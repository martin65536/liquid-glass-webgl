package androidx.lifecycle;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import defpackage.b80;
import defpackage.c80;
import defpackage.er;
import defpackage.n00;
import defpackage.r7;
import defpackage.sn0;
import defpackage.tn0;
import defpackage.v7;
import defpackage.z70;
import java.util.HashSet;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ProcessLifecycleInitializer implements n00 {
    @Override // defpackage.n00
    public final List a() {
        return er.e;
    }

    @Override // defpackage.n00
    public final Object b(Context context) {
        context.getClass();
        r7 t = r7.t(context);
        t.getClass();
        if (((HashSet) t.g).contains(ProcessLifecycleInitializer.class)) {
            if (!c80.a.getAndSet(true)) {
                Context applicationContext = context.getApplicationContext();
                applicationContext.getClass();
                ((Application) applicationContext).registerActivityLifecycleCallbacks(new b80());
            }
            tn0 tn0Var = tn0.m;
            tn0Var.getClass();
            tn0Var.i = new Handler();
            tn0Var.j.d(z70.ON_CREATE);
            Context applicationContext2 = context.getApplicationContext();
            applicationContext2.getClass();
            ((Application) applicationContext2).registerActivityLifecycleCallbacks(new sn0(tn0Var));
            return tn0Var;
        }
        v7.o("ProcessLifecycleInitializer cannot be initialized lazily.\n               Please ensure that you have:\n               <meta-data\n                   android:name='androidx.lifecycle.ProcessLifecycleInitializer'\n                   android:value='androidx.startup' />\n               under InitializationProvider in your AndroidManifest.xml");
        return null;
    }
}
