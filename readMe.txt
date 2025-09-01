project would engaged many Payment Service Providers (PSP):
- each PSP has its own model called native model

the goal is to e-commerce application can ease integrate with new PSPs as well as add new business
rules to current implementations

So we would need a


                                            | - native model Adyen
same db table---generic model--- mappers - native model Stripe
                                            | - native model Braintree

we have to map each native model into generic one and then save into db common info and rest in some more custom ones

PspProcessor {
IAsync
IAuthSync
}